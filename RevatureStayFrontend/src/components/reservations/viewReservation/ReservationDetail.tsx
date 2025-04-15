import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Grid, Paper, TextField, Typography } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers";
import axios from "axios";
import dayjs, { Dayjs } from "dayjs";
import { useState } from "react";
import iReservation from "../../../interfaces/iReservation";
import { useNavigate } from "react-router-dom";
import {URL} from "../../../util/path";

interface IReservationDetails{
    reservationId: number,
    checkInDate: string,
    checkOutDate: string,
    numGuests: number,
    status: string,
    setSuccess: (value:boolean)=>void
}

function ReservationDetails({reservationId, checkInDate, checkOutDate, numGuests, status, setSuccess} : IReservationDetails){
    const [checkin, setChecking] = useState<Dayjs|null>(dayjs(checkInDate));
    const [checkout, setCheckout] =useState<Dayjs|null>(dayjs(checkOutDate));
    const [guests, setGuests ] = useState<number>(numGuests);
    const [isDisabled, setDisabled] = useState<boolean>(true);
    const [checkinError, setCheckinError] = useState<boolean>(false);
    const [checkoutError, setCheckoutError] = useState<boolean>(false);
    const [guestsError, setGuestsError] = useState<boolean>(false);
    const [error, setError] = useState<String>("");
    const [cancelResponse, setCancelResponse] =useState<String>("");
    const [confirmation, setConfirmation] = useState<boolean>(false);
    const navigate = useNavigate();
    
    
    const validateFields = ()=>{
        const checkinError = checkin === null;
        const checkoutError = checkout === null;
        const guestsError = isNaN(guests) || guests <= 0;

        setCheckinError(checkinError)
        setCheckoutError(checkoutError)
        setGuestsError(guestsError)
        return !(checkinError || checkoutError || guestsError);
    }

    const sendPutRequest = async (data : iReservation) => {
        try{
            let res = await axios.put<iReservation>(`${URL}/reservations/${reservationId}`,
                data,
                {withCredentials: true}
            )
            setChecking(dayjs(res.data.checkInDate));
            setCheckout(dayjs(res.data.checkOutDate));
            setGuests(res.data.numGuests);
            setDisabled(true);
            setSuccess(true);

            } catch (error){
            if (axios.isAxiosError(error)) {
                const errorMessage = error.response?.data?.error || error.message;
                setError(errorMessage);
            } else {
                setError('Unknown error: ' + error);
            }    
        }
    }

    const makeReservationHandler = async () => {
        let isValid = validateFields();
        if(isValid){
            let reservation:iReservation = {
                checkInDate: checkin ? checkin.toISOString() : "",
                checkOutDate: checkout ? checkout.toISOString() : "",
                numGuests: guests,
                rooms: [],
                hotel: {
                    hotelId:0,
                    name: "",
                    address: "",
                    cellphoneNumber: "",
                    description: "",
                },
                reservationId:reservationId,
                user:{
                    userId: 0,     
                    firstName: "",
                    lastName: "",
                    email: "",
                    password: "",
                    role: "CUSTOMER"
                },
                status:'PENDING'
            }
            await sendPutRequest(reservation);
        }
    }

    const cancelHandler = async () => {
        try{
            await axios.patch<iReservation>(`${URL}/reservations/${reservationId}`,
                {status:"CANCELLED"},
                {withCredentials: true}
                
            )
            setCancelResponse("Your reservation was cancelled");

            } catch (error){
            if (axios.isAxiosError(error)) {
                const errorMessage = error.response?.data?.error || error.message;
                setError(errorMessage);
            } else {
                setError('Unknown error: ' + error);
            }    
        }
    }

    return (
        <Paper
            sx={{
                padding:'1rem',
                marginBottom: '1rem',
                boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)'
            }}
        >
            <Dialog open={error ? true: false} onClose={() => {setError("")}}>
                    <DialogTitle>Upps something went wrong!</DialogTitle>
                    <DialogContent>
                      <Typography>
                        {`${error} . Please try again`}
                        </Typography>
                    </DialogContent>
                </Dialog>
                <Dialog open={cancelResponse ? true: false} onClose={() => {navigate("/reservations")}}>
                    <DialogContent>
                      <Typography>
                        {cancelResponse}
                        </Typography>
                    </DialogContent>
                </Dialog>
                <Dialog open={confirmation} onClose={()=>{setConfirmation(false)}}>
                <DialogTitle>Cancel Reservation</DialogTitle>
                <DialogContent>
                <DialogContentText>
                    Do you really wants to cancel your reservation?
                </DialogContentText>
                </DialogContent>
                <DialogActions>
                <Button onClick={()=>{setConfirmation(false)}} color="primary">
                    Back
                </Button>
                <Button 
                    onClick={()=>{
                        cancelHandler();
                        setConfirmation(false);
                    }} 
                    color="error"
                >
                    Cancel Reservation
                </Button>
                </DialogActions>
            </Dialog>
            <Grid container spacing={2}>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Check-In Date:</Typography>
                    <DatePicker 
                        value={checkin} 
                        onChange={(value) => {
                            setChecking(value);
                            setDisabled(false);
                        }}
                        slotProps={{
                            textField: {
                              error: checkinError, 
                              helperText: checkinError ? 'You must provide a value' : '', 
                            },
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
                        slotProps={{
                            textField: {
                              error: checkoutError, 
                              helperText: checkoutError ? 'You must provide a value' : '', 
                            },
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
                        error={guestsError}
                        helperText={guestsError ? 'You must provide a valid number' : ''}
                    />
                </Grid>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Status:</Typography>
                    <TextField defaultValue={status} disabled/>
                </Grid>
                <Button 
                    variant="contained" 
                    color="primary" 
                    disabled={isDisabled}
                    onClick={makeReservationHandler}>
                    Save
                </Button>
                <Button variant="contained" color="error" onClick={()=> {setConfirmation(true)}}>Cancel Reservation</Button>
            </Grid>
            
        </Paper>
    );
}

export default ReservationDetails;