import { Box, Typography, Grid, Button } from "@mui/material";
import { IReservation } from "../../../interfaces/IReservation";
import ReservationAttribute from "./ReservationAttribute";
import { useState } from "react";
import ReservationDetailsButton from "./ReservationDetailsButton";
import ReservationDetails from "./ReservationDetails";

function ListedReservation( reservation : IReservation ){
  const [open, setOpen] = useState(false);

  const totalPrice = reservation.rooms.reduce((sum, room) => sum + room.price, 0);

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  return (
    <Box
      sx={{
        maxWidth: 1000,
        margin: "0.3rem auto",
        padding: "1rem",
        border: "1px solid #ccc",
        borderRadius: "8px",
        boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.1)",
        bgcolor:"#E2FCEF"
      }}
    >
      <Grid container spacing={2}>
        <ReservationAttribute value={reservation.checkInDate}/>
        <ReservationAttribute value={reservation.checkOutDate}/>
        <ReservationAttribute value={reservation.status}/>
        <ReservationAttribute value={reservation.numGuests.toString()}/>
        <ReservationAttribute value={reservation.hotel.name}/>
        <ReservationAttribute value={totalPrice.toString()} size={1}/>
        
        <Grid size={2}>
            <ReservationDetailsButton handler = {handleOpen}/>
        </Grid>
        <ReservationDetails reservation={reservation} handleClose={handleClose} open = {open} totalPrice={totalPrice}/>
      </Grid>

    </Box>
  );
};

export default ListedReservation;