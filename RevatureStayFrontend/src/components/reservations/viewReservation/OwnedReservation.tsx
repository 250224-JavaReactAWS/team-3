import { Container, Grid, Paper, Snackbar, Typography } from "@mui/material";
import iReservation from "../../../interfaces/iReservation";
import UserSection from "./UserSection";
import HotelSection from "./HotelSection";
import ReservationDetails from "./ReservationDetail";
import RoomSection from "./RoomSection";
import { useState } from "react";
import iRoom from "../../../interfaces/iRoom";
import OwnedReservationDetails from "./OwnedReservationDetails";

interface IReservationView{
    reservation : iReservation
}

function OwnedReservation({reservation}:IReservationView){
    const [success, setSuccess] = useState<boolean>(false);
    const [rooms, setRooms] = useState<iRoom[]>(reservation.rooms);

    const successHandler = (value:boolean)=>{
        setSuccess(value);
    }
    return (
        <Container>
            <Snackbar
                open={success}
                autoHideDuration={10000}
                onClose={()=>{setSuccess(false)}}
                message="Changes were saved successfully!"
                color="success"
            />
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
                        <OwnedReservationDetails 
                            checkInDate={reservation.checkInDate} 
                            checkOutDate={reservation.checkOutDate}
                            status={reservation.status ? reservation.status : ""}
                            numGuests={reservation.numGuests}
                            reservationId={reservation.reservationId ? reservation.reservationId : 0}
                            setSuccess={successHandler}
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
                        <RoomSection 
                            rooms={rooms} 
                            setRooms={(value:iRoom[])=>{setRooms(value)}}
                            reservationId={reservation.reservationId}
                        />
                    </Grid>
                </Grid>
            </Paper>
        </Container>
    );   
}

export default OwnedReservation;