import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { ThemeProvider } from '@mui/material/styles'
import CssBaseline from '@mui/material/CssBaseline'
import  { theme } from './theme'
import Header from './components/header'
import Reservation from './components/reservations/viewReservation/Reservation'
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import CreateReservation from './components/reservations/createReservation/CreateReservation'

const reservationData = [{
  reservationId: 2,
  checkInDate: "2025-11-11",
  checkOutDate: "2025-11-12",
  numGuests: 5,
  status: "PENDING",
  user: {
    userId: 3,
    firstName: "Jhon",
    lastName: "Doe",
    email: "customer@gmail.com",
    password: "Password1*",
    role: "CUSTOMER",
  },
  hotel: {
    hotelId: 2,
    name: "City Express",
    address: "1500 Test Street",
    cellphoneNumber: "1234567891",
    description: "Very nice hotel with nice views and nice food",
  },
  rooms: [
    {
      roomId: 2,
      type: "SUITE",
      beds: 3,
      baths: 2,
      price: 200.99,
      available: true,
    },
    {
      roomId: 3,
      type: "SINGLE",
      beds: 3,
      baths: 2,
      price: 200.99,
      available: true,
    },
    {
      roomId: 3,
      type: "SINGLE",
      beds: 3,
      baths: 2,
      price: 200.99,
      available: true,
    },
    {
      roomId: 3,
      type: "SINGLE",
      beds: 3,
      baths: 2,
      price: 200.99,
      available: true,
    },
    {
      roomId: 3,
      type: "SINGLE",
      beds: 3,
      baths: 2,
      price: 200.99,
      available: true,
    },
  ],
}]


createRoot(document.getElementById('root')!).render(
  <ThemeProvider theme={theme}>
    <LocalizationProvider dateAdapter={AdapterDayjs}>
    <CssBaseline />
    <Header></Header>
    <CreateReservation {...reservationData[0].hotel}/>
    {/* <Reservation {...reservationData[0]}/> */}
    </LocalizationProvider>
  </ThemeProvider>
)
