import { Box, Typography, Grid } from "@mui/material";
import { IReservation } from "../../interfaces/IReservation";

function ListedReservation( reservation : IReservation ){
  // Calculamos el precio total de las habitaciones
  const totalPrice = reservation.rooms.reduce((sum, room) => sum + room.price, 0);

  
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
        <Grid size={2}>
            <Typography variant="body2">{reservation.checkInDate}</Typography>
        </Grid>
        <Grid size={2}>

      <Typography variant="body2">{reservation.checkOutDate}</Typography>
        </Grid>
        <Grid  size={2}>
          <Typography variant="body2">{reservation.status}</Typography>
        </Grid>
        <Grid  size={2}>
          <Typography variant="body2">{reservation.numGuests}</Typography>
        </Grid>
        <Grid  size={2}>
          <Typography variant="body2"> {reservation.hotel.name}</Typography>
        </Grid>
        <Grid  size={2}>
          <Typography variant="body2">
            ${totalPrice.toFixed(2)}
          </Typography>
        </Grid>
      </Grid>

    </Box>
  );
};

export default ListedReservation;