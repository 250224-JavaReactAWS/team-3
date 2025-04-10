import { Button, Grid, Paper, TextField, Typography } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers";
import { Dayjs } from "dayjs";
import { useState } from "react";

interface IReservationDetailsForm{
    checkin:Dayjs | null,
    checkout:Dayjs | null,
    guests:number,
    setCheckin: (value :Dayjs | null)=>void,
    setCheckout: (value :Dayjs | null)=>void,
    setGuests:(value: number)=>void
}
function ReservationDetailsForm({checkin, setCheckin, checkout, setCheckout, guests, setGuests}:IReservationDetailsForm){
    
    
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
                            setCheckin(value);
                        }}
                    />
                </Grid>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Check-Out Date:</Typography>
                    <DatePicker 
                        value={checkout}
                        onChange={(value) => {
                            setCheckout(value);
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
                        }}
                    />
                </Grid>
            </Grid>
            
        </Paper>
    );
}

export default ReservationDetailsForm;