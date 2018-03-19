import React from 'react';
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import IconButton from 'material-ui/IconButton';
import Typography from "material-ui/Typography";
import Grid from "material-ui/Grid";
import PropTypes from 'prop-types';
import 'typeface-roboto'
import Progress from './Progress'
import './Main.css';
import faInfoCircle from '@fortawesome/fontawesome-free-solid/faInfoCircle'
import FontAwesomeIcon from '@fortawesome/react-fontawesome'
import SignIn from '../containers/SignInContainer';

const NavBar = ({ progressHidden, actions }) => {
  return (

    <AppBar color="primary">

      <Toolbar>
        <Grid container direction="row" justify="space-between" alignItems="center" alignContent="center">
          <Grid item>
            <Grid container direction="row" alignItems="center">
              <Grid item>
                <Typography variant="title" color="inherit">FENERGY</Typography>
              </Grid>
              <Grid item>
                <Progress hidden={progressHidden} />
              </Grid>
            </Grid>
          </Grid>
          <Grid item>
            <SignIn />
            <IconButton color="inherit">
              <FontAwesomeIcon icon={faInfoCircle} onClick={actions.healthCheck}/>
            </IconButton>
          </Grid>
        </Grid>
      </Toolbar>

    </AppBar>

  )
}

NavBar.propTypes = {
  progressHidden: PropTypes.bool,
  actions: PropTypes.shape({
    healthCheck: PropTypes.func
  })
};

export default NavBar