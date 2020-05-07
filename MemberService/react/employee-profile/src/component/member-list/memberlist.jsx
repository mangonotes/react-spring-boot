import React, { Component } from 'react';
import { Button, Image, Table } from 'react-bootstrap';
import Popup from '../popup/';

class Memberlist extends Component {
    constructor(props) {
        super(props);
        this.state = {
            members: props.members,
        }
    }
    
    static getDerivedStateFromProps(nextProps, prevState) {
      console.log(nextProps);
      if (prevState.members !== nextProps.members) {
        return {
          members: nextProps.members,
        };
      }
      // Return null to indicate no change to state.
      return null;
    }
   
    render() {
      const members = this.state.members;
      const rows = members && members.response &&
        members.response.map((member, i) => {
          const image = `/download/${member.id}`;
          return (<tr key={i}>
            <td> <Button variant="link" onClick={() =>
              this.props.updateMember(member)}>{member.id}</Button></td>
            <td>{member.firstName}</td>
            <td>{member.lastName}</td>
            <td>{member.dateOfBirth}</td>
            <td>
              <Popup title="Profile Image" link="image">
                <Image src={image} thumbnail />
              </Popup>
            </td>
          </tr>);
        });

      return (
        <div>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>#</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Date of birth</th>
                <th>Profile image</th>
              </tr>
            </thead>
            <tbody>
              {rows}
            </tbody>
          </Table>
        </div>
      );
    }
}

export default Memberlist;