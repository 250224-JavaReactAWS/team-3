import { Grid, Paper, TextField, Typography } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers";
import { Dayjs } from "dayjs";

interface IReservationDetailsForm{
    checkin:Dayjs | null,
    checkout:Dayjs | null,
    guests:number,
    setCheckin: (value :Dayjs | null)=>void,
    setCheckout: (value :Dayjs | null)=>void,
    setGuests:(value: number)=>void,
    checkinError:boolean,
    checkoutError:boolean,
    guestsError:boolean
}
function ReservationDetailsForm(fields:IReservationDetailsForm){
    
    
    
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
                        value={fields.checkin} 
                        onChange={(value) => {
                            fields.setCheckin(value);
                        }}
                        slotProps={{
                            textField: {
                              error: fields.checkinError, 
                              helperText: fields.checkinError ? 'You must provide a value' : '', 
                            },
                          }}
                  
                    />
                </Grid>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Check-Out Date:</Typography>
                    <DatePicker 
                        value={fields.checkout}
                        onChange={(value) => {
                            fields.setCheckout(value);
                        }}
                        slotProps={{
                            textField: {
                              error: fields.checkoutError, 
                              helperText: fields.checkoutError ? 'You must provide a value' : '', 
                            },
                          }}
                    />
                </Grid>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Number of Guests:</Typography>
                    <TextField 
                        defaultValue={fields.guests} 
                        type="number"
                        onChange={(event) => {
                            fields.setGuests(parseInt(event.target.value));
                        }}
                        error={fields.guestsError}
                        helperText={fields.guestsError ? 'You must provide a valid number' : ''}
                    />
                </Grid>
            </Grid>
            
        </Paper>
    );
}

export default ReservationDetailsForm;