import { ChangeEvent, useState, SyntheticEvent, useContext } from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline'
import Divider from '@mui/material/Divider';
import FormLabel from '@mui/material/FormLabel';
import FormControl from '@mui/material/FormControl';
import Link from '@mui/material/Link';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import MuiCard from '@mui/material/Card';
import { styled } from '@mui/material/styles';
import IUser from '../../interfaces/iUser';
import axios from 'axios';
import { authContext } from '../../App';
import { useNavigate } from 'react-router-dom';
import { FormControlLabel, Switch } from '@mui/material';

const Card = styled(MuiCard)(({ theme }) => ({
  display: 'flex',
  flexDirection: 'column',
  alignSelf: 'center',
  width: '100%',
  padding: theme.spacing(4),
  gap: theme.spacing(2),
  margin: 'auto',
  [theme.breakpoints.up('sm')]: {
    maxWidth: '450px',
  },
  boxShadow:
    'hsla(208, 53%, 32%, 0.45) -3px 9px 20px 0px'
}));

const SignUpContainer = styled(Stack)(({ theme }) => ({
  minHeight: '100vh',
  padding: theme.spacing(2),
  overflowY: 'auto',
  [theme.breakpoints.up('sm')]: {
    padding: theme.spacing(4),
  }
}));

export default function Register() {
  const passwordRegex: RegExp = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
  const emailRegex: RegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  // States
  const [email, setEmail] = useState('');
  const [emailError, setEmailError] = useState(false);
  const [emailErrorMessage, setEmailErrorMessage] = useState('');

  const [password, setPassword] = useState('');
  const [passwordError, setPasswordError] = useState(false);
  const [passwordErrorMessage, setPasswordErrorMessage] = useState('');

  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [errorName, setErrorName] = useState(false);
  const [roleBool, setRoleBool] = useState(false);

  const roleReference = useContext(authContext)
  const navigate = useNavigate();


  let register = async () => {
    try {
      const role = roleBool ? "OWNER" : "CUSTOMER";
      let res = await axios.post<IUser>('http://localhost:8080/users/register',
        { firstName, lastName, email, password, role },
        { withCredentials: true }
      )
      roleReference?.setRole(res.data.role);
      navigate('/');

    } catch (err) {
      roleReference?.setRole("UNAUTHENTICATED")
    }
  }

  let submitForm = async (e: SyntheticEvent<HTMLFormElement>) => {
    e.preventDefault()
    let isValidate = true;
    if (!(email || emailRegex.test(email))) {
      setEmailError(true);
      setEmailErrorMessage('Invalid Email')
      isValidate = false
    } else {
      setEmailError(false);
      setEmailErrorMessage('');
    }

    if (!(password || passwordRegex.test(password))) {
      setPasswordError(true);
      setPasswordErrorMessage("Invalid Password. Must be at least 8 characters long and need to contain one uppercase letter (A-Z), one lowercase letter (a-z), one number (0-9), and one special character (@$!%*?&)")
      isValidate = false;
    } else {
      setPasswordError(false);
      setPasswordErrorMessage('');
    }

    if (!(firstName || lastName)) {
      setErrorName(true);
      isValidate = false;
    } else {
      setErrorName(false);
    }

    if (isValidate) {
      register();
    }
  }

  return (
    <>
      <CssBaseline enableColorScheme />
      <SignUpContainer direction="column" justifyContent="space-between">
        <Card variant="outlined">
          <Typography
            component="h1"
            variant="h4"
            sx={{ width: '100%', fontSize: 'clamp(2rem, 10vw, 2.15rem)' }}
          >
            Register
          </Typography>
          <Box
            component="form"
            onSubmit={submitForm}
            noValidate
            sx={{
              display: 'flex',
              flexDirection: 'column',
              width: '100%',
              gap: 2,
              margin: '2rem 0rem'
            }}
          >
            <FormControl>
              <FormLabel htmlFor="firstName">First Name</FormLabel>
              <TextField
                error={errorName}
                helperText={errorName ? 'First Name is Required' : ''}
                name="firstName"
                placeholder="First Name"
                type="text"
                id="firstName"
                autoComplete="name"
                autoFocus
                required
                fullWidth
                margin='dense'
                variant="outlined"
                onChange={(e: ChangeEvent<HTMLInputElement>) => setFirstName(e.target.value)}
                color={errorName ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="lastName">Last Name</FormLabel>
              <TextField
                error={errorName}
                helperText={errorName ? 'Last Name is Required' : ''}
                name="lastName"
                placeholder="Last Name"
                type="text"
                id="lastName"
                autoComplete="name"
                autoFocus
                required
                fullWidth
                margin='dense'
                variant="outlined"
                onChange={(e: ChangeEvent<HTMLInputElement>) => setLastName(e.target.value)}
                color={errorName ? "error" : "primary"}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="email">Email</FormLabel>
              <TextField
                error={emailError}
                helperText={emailErrorMessage}
                id="email"
                type="email"
                name="email"
                placeholder="your@email.com"
                autoComplete="email"
                autoFocus
                required
                fullWidth
                variant="outlined"
                margin='dense'
                color={emailError ? 'error' : 'primary'}
                onChange={(e: ChangeEvent<HTMLInputElement>) => setEmail(e.target.value)}
              />
            </FormControl>
            <FormControl>
              <FormLabel htmlFor="password">Password</FormLabel>
              <TextField
                error={passwordError}
                helperText={passwordErrorMessage}
                name="password"
                placeholder="••••••"
                type="password"
                id="password"
                autoComplete="current-password"
                autoFocus
                required
                fullWidth
                margin='dense'
                variant="outlined"
                color={passwordError ? 'error' : 'primary'}
                onChange={(e: ChangeEvent<HTMLInputElement>) => setPassword(e.target.value)}
              />
            </FormControl>
            <FormControl>
              <FormControlLabel
                control={<Switch
                  color='secondary'
                  onChange={(e: ChangeEvent<HTMLInputElement>) => setRoleBool(e.target.checked)}
                />}
                label="You wanna register as a owner?" />
            </FormControl>
            <Button
              type="submit"
              fullWidth
              variant="contained"
            >
              Register
            </Button>
          </Box>
          <Divider>or</Divider>
          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <Typography sx={{ textAlign: 'center' }}>
              You have an account?{' '}
              <Link
                variant="body2"
                sx={{ alignSelf: 'center', cursor: 'pointer' }}
                onClick={() => navigate('/login')}
              >
                Login
              </Link>
            </Typography>
          </Box>
        </Card>
      </SignUpContainer>
    </>
  );
}
