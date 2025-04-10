import { Button, Grid, Paper, TextField, Typography } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers";
import dayjs, { Dayjs } from "dayjs";
import { useState } from "react";

interface IReservationDetails{
    reservationId: number;
    checkInDate: string;
    checkOutDate: string;
    numGuests: number;
    status: string;
}

function ReservationDetails({reservationId, checkInDate, checkOutDate, numGuests, status} : IReservationDetails){
    const [checkin, setChecking] = useState<Dayjs|null>(dayjs(checkInDate));
    const [checkout, setCheckout] =useState<Dayjs|null>(dayjs(checkOutDate));
    const [guests, setGuests ] = useState<number>(numGuests);
    const [isDisabled, setDisabled] = useState<boolean>(true);
    
    return (
        <Paper
            sx={{
                padding:'1rem',
                marginBottom: '1rem',
                boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)'
            }}
        >
            <Grid container spacing={2}>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Check-In Date:</Typography>
                    <DatePicker 
                        value={checkin} 
                        onChange={(value) => {
                            setChecking(value);
                            setDisabled(false);
                        }}
                    />
                </Grid>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Check-Out Date:</Typography>
                    <DatePicker 
                        value={checkout}
                        onChange={(value) => {
                            setCheckout(value);
                            setDisabled(false);
                        }}
                    />
                </Grid>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Number of Guests:</Typography>
                    <TextField 
                        defaultValue={guests} 
                        type="number"
                        onChange={(event) => {
                            setGuests(parseInt(event.target.value));
                            setDisabled(false);
                        }}
                    />
                </Grid>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Status:</Typography>
                    <TextField defaultValue={status} disabled/>
                </Grid>
                <Button variant="contained" color="primary" disabled={isDisabled}>
                    Save
                </Button>
                <Button variant="contained" color="error">Cancel Reservation</Button>
            </Grid>
            
        </Paper>
    );
}

export default ReservationDetails;