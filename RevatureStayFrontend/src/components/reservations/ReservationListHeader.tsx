import { Box, Grid, Typography } from "@mui/material";

function ReservationListHeader (){
    return (
        <Box
      sx={{
        maxWidth: 1000,
        margin: "0.5rem auto",
        padding: "1rem",
        border: "1px solid #ccc",
        borderRadius: "8px",
        boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.1)",
        bgcolor:"#26547C",
        color:"#ffffff"
      }}
      color="secondary"
    >
      <Grid container spacing={1}>
        <Grid size={2}>
            <Typography variant="body1"><strong>Check-In</strong></Typography>
        </Grid>
        <Grid size={2}>

      <Typography variant="body1"><strong>Check-Out</strong></Typography>
        </Grid>
        <Grid  size={2}>
          <Typography variant="body1"><strong>Status</strong></Typography>
        </Grid>
        <Grid  size={2}>
          <Typography variant="body1"><strong>Guests</strong></Typography>
        </Grid>
        <Grid  size={2}>
          <Typography variant="body1"><strong>Hotel</strong></Typography>
        </Grid>
        <Grid  size={2}>
          <Typography variant="body1">
            <strong>Total Price</strong>
          </Typography>
        </Grid>
      </Grid>

    </Box>
    );
}

export default ReservationListHeader;