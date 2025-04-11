import { Box, Grid, Button } from "@mui/material";
import iReservation  from "../../../interfaces/iReservation";
import ReservationAttribute from "./ReservationAttribute";
import { useNavigate } from "react-router-dom";
function ListedReservation( reservation : iReservation ){

  const totalPrice = reservation.rooms.reduce((sum, room) => sum + room.price, 0);
  const navigate = useNavigate();


  return (
    <Box
      sx={{
        maxWidth: 1000,
        margin: "0.3rem auto",
        padding: "1rem",
        border: "1px solid #ccc",
        borderRadius: "8px",
        boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.1)",
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
            <Button onClick={()=>navigate(`/reservations/${reservation.reservationId}` )}>Details</Button>
        </Grid>
      </Grid>

    </Box>
  );
};

export default ListedReservation;