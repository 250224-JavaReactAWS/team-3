import { CssBaseline } from "@mui/material";
import { ThemeProvider } from "@emotion/react";
import { theme } from "./theme";
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Navbar from "./components/Navbar";
import Hotels from "./components/HotelManagment/Hotels";
import HotelView from "./components/HotelManagment/HotelView";
import Dashboard from "./components/Owner/Dashboard";
import Images from "./components/HotelManagment/Images Managment/Images";
import Rooms from "./components/HotelManagment/Rooms Managment/Rooms";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import Login from "./components/user/Login";
import { createContext, useEffect, useState } from "react";
import axios from "axios";
import Register from "./components/user/Register";
import { LocalizationProvider } from "@mui/x-date-pickers";
import ReservationDisplayer from "./components/reservations/viewReservation/ReservationDisplayer";
import CreateReservationDisplayer from "./components/reservations/createReservation/CreateReservationDisplayer";
import ReservationsList from "./components/reservations/viewMyReservations/ReservationsList";
import { URL } from "./util/path";


export interface AuthContextType {
  role: "CUSTOMER" | "OWNER" | "UNAUTHENTICATED",
  setRole: (role: "CUSTOMER" | "OWNER" | "UNAUTHENTICATED") => void
}

export const authContext = createContext<AuthContextType | null>(null)

export default function App() {
  const [role, setRole] = useState<"CUSTOMER" | "OWNER" | "UNAUTHENTICATED">("UNAUTHENTICATED");

  useEffect(() => {
    axios.get<"CUSTOMER" | "OWNER">(
      `${ URL }/users/session`, { withCredentials: true }
    )
      .then(res => {
        setRole(res.data)
        console.log(res);
      })
      .catch(err => {
        setRole("UNAUTHENTICATED");
        console.log(err);
      })
  }, [])
  return (
    <ThemeProvider theme={theme}>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <CssBaseline />
        <authContext.Provider value={{ role, setRole }}>
          <BrowserRouter>
            <Navbar />
            <Routes>
              <Route path='/' element={<Hotels />} />
              <Route path='/login' element={<Login />} />
              <Route path='/register' element={<Register />} />
              <Route path="/hotels/:hotelId" element={<HotelView />} />
              <Route path="/my-hotels" element={<Dashboard />} />
              <Route path="/my-hotels/:hotelId/:hotelName/rooms" element={<Rooms />} />
              <Route path="/my-hotels/:hotelId/:hotelName/images" element={<Images />} />
              <Route path="/reservations" element={<ReservationsList />} />
              <Route path="reservations/create/:hotelId" element={<CreateReservationDisplayer />} />
              <Route path="/reservations/:reservationId" element={<ReservationDisplayer />} />
            </Routes>
          </BrowserRouter>
        </authContext.Provider>
      </LocalizationProvider>
    </ThemeProvider>
  )
}