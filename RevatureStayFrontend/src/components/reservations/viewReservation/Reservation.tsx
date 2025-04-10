import { Container, Grid, Paper, Typography } from "@mui/material";
import { IReservation } from "../../../interfaces/IReservation";
import UserSection from "./UserSection";
import HotelSection from "./HotelSection";
import ReservationDetails from "./ReservationDetail";
import RoomSection from "./RoomSection";


function Reservation(reservation:IReservation){
    return (
        <Container>
            <Paper 
            sx={{
            padding: '16px',
            marginBottom: '16px',
            boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
            }}
            >
                <Grid container spacing={2}>
                    <Grid size={12}>
                        <Typography variant="h5">Reservation Details</Typography>
                        <ReservationDetails 
                            checkInDate={reservation.checkInDate} 
                            checkOutDate={reservation.checkOutDate}
                            status={reservation.status}
                            numGuests={reservation.numGuests}
                            reservationId={reservation.reservationId}
                        />
                    </Grid>
                    <Grid size={12}>
                        <Typography variant="h5" gutterBottom>User Details:</Typography>
                        
                            <UserSection {...reservation.user} />
                        
                    </Grid>
                    <Grid size={12}>
                        <Typography variant="h5" gutterBottom>Hotel Details:</Typography>
                        <HotelSection {...reservation.hotel} />
                    </Grid>
                    <Grid size={12}>
                        <Typography variant="h5" gutterBottom>Rooms:</Typography>
                        <RoomSection rooms={reservation.rooms}/>
                    </Grid>
                </Grid>
            </Paper>
        </Container>
    );   
}

export default Reservation;