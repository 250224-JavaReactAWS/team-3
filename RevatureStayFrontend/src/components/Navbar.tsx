import { useState, MouseEvent, useContext } from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import MeetingRoomIcon from '@mui/icons-material/MeetingRoom';
import { authContext } from '../App';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { URL } from '../util/path';

export default function Navbar() {
  const [anchorElNav, setAnchorElNav] = useState<null | HTMLElement>(null);
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);

  const navigate = useNavigate();

  const roleReference = useContext(authContext);

  const handleOpenNavMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  const logout = async () => {
    try {
      let res = await axios.post<any>(`${URL}/users/logout`, { withCredentials: true }
      );
      console.log(res);
      navigate('/');
      roleReference?.setRole("UNAUTHENTICATED")
    } catch (err) {
      console.log(err);
    }
  }

  return (
    <AppBar position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <MeetingRoomIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: .5 }} />
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontWeight: 900,
              letterSpacing: '.15rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
            Rev<span style={{ color: "hsla(208, 53%, 32%, .8)" }}>Stay</span>
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{ display: { xs: 'block', md: 'none' } }}>
            </Menu>
          </Box>
          <MeetingRoomIcon sx={{ display: { xs: 'flex', md: 'none' } }} />
          <Typography
            variant="h5"
            noWrap
            component="a"
            href="#app-bar-with-responsive-menu"
            sx={{
              mr: 2,
              display: { xs: 'flex', md: 'none' },
              flexGrow: 1,
              fontWeight: 900,
              letterSpacing: '.2rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
            Rev<span style={{ color: "hsla(208, 53%, 32%, .8)" }}>Stay</span>
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
          </Box>
          {roleReference?.role == "UNAUTHENTICATED" ?
            <Box sx={{ flexGrow: 0, display: { xs: 'flex' } }}>
              <Button
                key="login"
                onClick={() => navigate('/login')}
                sx={{ my: 2, color: 'white', display: 'block' }}
              >
                Login
              </Button>
              <Button
                key="register"
                onClick={() => navigate('/register')}
                sx={{ my: 2, color: 'white', display: 'block' }}
              >
                Register
              </Button>
            </Box> :
            <Box sx={{ flexGrow: 0 }}>
              <Tooltip title="Open settings">
                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                  <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
                </IconButton>
              </Tooltip>
              <Menu
                sx={{ mt: '45px' }}
                id="menu-appbar"
                anchorEl={anchorElUser}
                anchorOrigin={{
                  vertical: 'top',
                  horizontal: 'right',
                }}
                keepMounted
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'right',
                }}
                open={Boolean(anchorElUser)}
                onClose={handleCloseUserMenu}
              >
                {(roleReference?.role === "OWNER") && 
                    <MenuItem key="My Hotels" onClick={() => navigate('/my-hotels')}>
                      <Typography sx={{ textAlign: 'center' }}>My Hotels</Typography>
                    </MenuItem>
                }
                <MenuItem key="Reservations" onClick={() => navigate('/reservations')}>
                      <Typography sx={{ textAlign: 'center' }}>Reservations</Typography>
                </MenuItem>
                <MenuItem key="Logout" onClick={logout}>
                      <Typography sx={{ textAlign: 'center' }}>Logout</Typography>
                </MenuItem>
              </Menu>
            </Box>
          }
        </Toolbar>
      </Container>
    </AppBar>
  );
}
