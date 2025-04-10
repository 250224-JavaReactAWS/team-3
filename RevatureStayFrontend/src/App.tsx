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


export default function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path="/" element={<Hotels/>}/>
          <Route path="/hotels/:hotelId" element={<HotelView/>}/>
          <Route path="/my-hotels" element={<Dashboard/>}/>
          <Route path="/my-hotels/:hotelId/rooms" element={<Rooms/>} />
          <Route path="/my-hotels/:hotelId/images" element={<Images/>} />
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  )
}