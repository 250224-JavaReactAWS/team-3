import { CssBaseline } from "@mui/material";
import { ThemeProvider } from "@emotion/react";
import { theme } from "./theme";
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Navbar from "./components/Navbar";
import Login from "./components/user/Login";
import { createContext, useEffect, useState } from "react";
import axios from "axios";
import Hotels from "./components/HotelManagment/Hotels";
import Register from "./components/user/Register";

export interface AuthContextType {
  role: "CUSTOMER" | "OWNER" | "UNAUTHENTICATED",
  setRole: (role: "CUSTOMER" | "OWNER" | "UNAUTHENTICATED") => void
}

export const authContext = createContext<AuthContextType | null>(null)

export default function App() {
  const [role, setRole] = useState<"CUSTOMER" | "OWNER" | "UNAUTHENTICATED">("UNAUTHENTICATED");

  useEffect(() => {
    axios.get<"CUSTOMER" | "OWNER">("http://localhost:8080/users/session", {withCredentials:true })
      .then(res => {
        setRole(res.data)
        console.log(res);
      })
      .catch(err => {
        setRole("UNAUTHENTICATED");
        console.log(err);
  })}, [])
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <authContext.Provider value={{role, setRole}}>
        <BrowserRouter>
          <Navbar />
          <Routes>
            <Route path='/hotels' element={<Hotels />}/>
            <Route path='/login' element={<Login />}/>
            <Route path='/register' element={<Register />}/>
          </Routes>
        </BrowserRouter>
      </authContext.Provider>
    </ThemeProvider>
  )
}