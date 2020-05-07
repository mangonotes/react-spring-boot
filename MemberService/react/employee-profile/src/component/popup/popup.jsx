import React from 'react';
import { Button, OverlayTrigger, Popover } from 'react-bootstrap';

const Popup = props => {
  const popover = (
    <Popover id="popover-basic">
      <Popover.Title as="h3">{props.title}</Popover.Title>
      <Popover.Content>
        {props.children}
      </Popover.Content>
    </Popover>
  );
  return (
    <OverlayTrigger trigger="click" placement="right" overlay={popover}>
      <Button variant="link">{props.link}</Button>
    </OverlayTrigger>
  );
};

export default Popup;