import React, { Component } from 'react';
import { Button } from 'react-bootstrap';
import { apiFetch } from '../../helpers/helpers';
import Memberlist from '../member-list/';
import Profile from '../modal/';

class MemberHome extends Component {
    constructor(props) {
        super(props);
        this.state = {
            members: {},
            member: {},
            modal : false,
            update : false
        }
    }

    componentDidMount() {
        this.updateMembersList();
    }

    showModal = () => {
        this.setState({
            modal : true,
            member : {},
            update: false
        });
    }

    toggleCreate = () => {
        this.setState({ modal: !this.state.modal });
    }

    updateMembersList = () => {
        apiFetch('/api/v1/members/find/all')
            .then(res => this.setState({ members: res }))
            .catch(err => console.log(err));
    }

    updateMember = (member) => {
        this.setState({
            member : member,
            update : true,
            modal : true,
        });
    }

    render() {
        return (
            <div className="row mb-4">
                <div className="col-sm-12 grid-margin">
                    <div class="mt-2 text-right">
                        <Button variant="primary" onClick={this.showModal}>
                            Create Profile
                        </Button>
                    </div>
                    <div class="mt-2">
                        <Profile 
                            updateMembersList={this.updateMembersList} 
                            showModal={this.state.modal}
                            toggle={this.toggleCreate} 
                            member={this.state.member} 
                            update={this.state.update} />
                        <Memberlist
                            members={this.state.members}
                            updateMember={this.updateMember} />
                    </div>
                </div>
            </div>
        );
    }
}

export default MemberHome;