import React from 'react';

const WarningBanner = props => {
    const message = props.deleted ? 'deleted profile' : 'created profile';
    console.log(props.response);
    if (props.response && props.response.status.code === 200) {
      return (<h2>
        Successfully {message}
      </h2>);
    }
    else if (props.response && props.response.status.code !== 200) {
      return (
        <h2>
          Not {message} error code : {this.state.response.status.code}
        </h2>
      );
    }
    return (
      <h2>
        Not {message}, Please check back end service.
      </h2>
    );
  
  };

export { WarningBanner };
