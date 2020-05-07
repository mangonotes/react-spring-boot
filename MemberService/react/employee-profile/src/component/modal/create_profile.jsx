import React, { Component } from 'react';
import { Button, Form, Image, Modal } from 'react-bootstrap';
import DatePicker from 'react-date-picker';
import ReactDOM from 'react-dom';
import { IoIosCalendar } from 'react-icons/io';
import { apiDelete, apiPost, apiPut, fileToBase64 } from '../../helpers/helpers';
import { WarningBanner } from '../shared/shared';
import './create-profile.css';

class Profile extends Component 
{
  /* Form validation for first name and last name. */
  formValidations(event) {
    const id = event.target.id;
    let fieldValidationErrors = this.state.errors;
    switch (id) {
      case 'firstName':
        const firstNameMatch = this.state.firstName.match(/^[a-zA-Z ]{3,20}$/);
        fieldValidationErrors.firstName = firstNameMatch ? '' : ' is invalid';
        break;
      case 'lastName':
        const lastNameMatch = this.state.lastName.match(/^[a-zA-Z ]{3,20}$/);
        fieldValidationErrors.lastName = lastNameMatch ? '' : ' is invalid';
        break;
      default :
        break;
    }
    this.setState({ errors: fieldValidationErrors });
  }

  /* Update state on date field change. */
  handleChangeDate= (value) => {
    this.setState({
      date: value
    });
  }

  /* Update state with  base64 string from profile picture. */
  handleFile = async (value) => {
    const file = ReactDOM.findDOMNode(this.refs.file).files[0];
    const result = await fileToBase64(file);
    this.setState({
      image:
      {
        file: result,
        fileType: file.type,
        fileName: file.name
      }
    });
  }

  /* Update state from form fields. */
  updateState() {
    this.setState(
      {
        firstName: ReactDOM.findDOMNode(this.refs.firstName).value,
        lastName: ReactDOM.findDOMNode(this.refs.lastName).value,
      });
  }

  /* Submit the form. */
  handleSubmit(e) {
    e.preventDefault();
    const body = {
      "request": {
        firstName: this.state.firstName,
        lastName: this.state.lastName,
        dateOfBirth: this.state.date,
        image: this.state.image
      }
    }

    if (this.state.update) {
      apiPut(`api/v1/members/update/${this.state.member.id}`, body)
      .then(res => {
        this.setState({ response: res });
        this.props.updateMembersList();
      })
      .catch(err => console.log(err));
    }
    else {
      apiPost('api/v1/members/create', body)
      .then(res => {
          this.setState({ response: res });
          this.props.updateMembersList();
        })
        .catch(err => console.log(err));
    }

    this.setState({
      show: true,
    });
  }
  
  constructor(props) {
    super(props);

    this.state = {
      modal: false,
      firstName: '',
      lastName: '',
      errors: { email: '', password: '' },
      show: false,
      response: '',
      date: '',
      model: props.showModel,
      member : props.member,
      update : false,
      delete : false,
      deleted: false
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.updateState = this.updateState.bind(this);
    this.formValidations = this.formValidations.bind(this);
  };

  static getDerivedStateFromProps(nextProps, prevState) {
    if (prevState.showModal !== nextProps.showModal && !prevState.deleted ) {
      const { firstName, lastName,dateOfBirth, id } = nextProps.member;
      if (nextProps.update && (nextProps.member !== prevState.member) ){
      return {
        modal: nextProps.showModal,
        firstName : firstName,
        lastName : lastName,
        date: dateOfBirth,
        member:nextProps.member, 
        update: nextProps.update,
        id : id
      };
    }else{
     return { modal: nextProps.showModal,
      update :nextProps.update,
     };
    }
  }
  // Return null for no changes.
  return null;
  }

  /* Show modal for popup. */
  showModal = (status) => {
    this.setState({
      show: status,
      delete: false,
      deleted: false
    });

    if (status === false && this.state.response &&
      this.state.response.status.code === 200) {
      this.props.toggle();
    }
  }

  /* Delete the profile. */
  deleteProfile = () => {
    const body = {};
    apiDelete(`api/v1/members/delete/${this.state.member.id}`, body)
    .then(res => {
        this.setState({ response: res, deleted: true, delete:false,show:  true });
        this.props.updateMembersList();
      })
      .catch(err => console.log(err));
  }
  setDelete = (status, e) => {
    e.preventDefault();
    this.setState({
      delete:status
    });
  }

  render() {
    const {update, id } = this.state;
    const buttonTitle = update ? "Update profile" :  "Create Profile";
    const image = `/download/${id}`;
    return (
      <div>
        <Modal show={this.state.modal} onHide={this.props.toggle}>
          <Modal show={this.state.show} onHide={() => this.showModal(false)}
            variant="success">
            <Modal.Header closeButton>
              <Modal.Title>Create Profile</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <WarningBanner deleted={this.state.deleted} response={this.state.response} />
              <hr />
              <div className="d-flex justify-content-end">
                <Button onClick={() => this.showModal(false)}
                  variant="outline-success">
                  Close
                </Button>
              </div>
            </Modal.Body>
          </Modal>
          <Modal show={this.state.delete} onHide={(e) => this.setDelete(false,e)}
            variant="success">
            <Modal.Header closeButton>
              <Modal.Title>Delete Profile</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              You would like to delete
              <hr />
              <div className="d-flex justify-content-end">
                <Button onClick={(e) => this.setDelete(false,e)}
                  variant="outline-success">
                  Close
                </Button>
                <Button onClick={(e)=> this.deleteProfile()}
                  variant="outline-success">
                  Delete
                </Button>
              </div>
            </Modal.Body>
          </Modal>
          <Modal.Header closeButton>
            <Modal.Title>Create Profile</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form onSubmit={this.handleSubmit} >
              <Form.Group controlId="firstName">
                <Form.Label>First name</Form.Label>
                <Form.Control type="text"
                  placeholder="Please enter your first name"
                  isInvalid={this.state.errors.firstName}
                  ref="firstName" value={this.state.firstName}
                  onChange={this.updateState} onBlur={this.formValidations}
                />
                <Form.Text className="text-muted">
                  Your first name
                </Form.Text>
                <Form.Control.Feedback type="invalid">
                  required atleast 3 character maximum 30
                 </Form.Control.Feedback>
                <Form.Control.Feedback type="valid">
                  Valid first name
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group controlId="lastName">
                <Form.Label>Last Name</Form.Label>
                <Form.Control type="text" placeholder="lastName"
                  ref="lastName" isInvalid={this.state.errors.lastName}
                  value ={this.state.lastName}
                  onChange={this.updateState} onBlur={this.formValidations} />
                <Form.Text className="text-muted">
                  Your last name
                </Form.Text>
                <Form.Control.Feedback type="invalid">
                  required atleast 3 character maximum 30
                 </Form.Control.Feedback>
                <Form.Control.Feedback type="valid">
                  Valid last name
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group>
                <Form.Label>Date of birth</Form.Label>
                  <DatePicker  calendarIcon={<IoIosCalendar />}
                    className="form-control"
                    onChange={this.handleChangeDate}
                    value={this.state.date} tileClassName=""
                    />
              </Form.Group>
              <Form.Group>
              { update && <Image src={image} thumbnail />}
              <Form.File id="file" onChange={this.handleFile}>
                <Form.File.Label>Image file to upload</Form.File.Label>
                <Form.File.Input ref="file" onChange={this.handleFile} />
              </Form.File>
              </Form.Group>
              <Button variant="primary" className="mr-1" type="submit">
                {buttonTitle}
              </Button>
              {this.state.update &&  <Button variant="danger" type="submit" onClick={(e)=> this.setDelete(true,e)}>
                Delete
              </Button> }
            </Form>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={this.props.toggle}>
              Close
             </Button>
          </Modal.Footer>
        </Modal>
      </div>
    );
  }
}

export default Profile;