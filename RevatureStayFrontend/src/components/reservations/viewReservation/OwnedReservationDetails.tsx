import { Alert, Button, Dialog, DialogContent, DialogTitle, Grid, Paper, Snackbar, TextField, Typography } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers";
import axios from "axios";
import dayjs  from "dayjs";
import { useState } from "react";
import iReservation from "../../../interfaces/iReservation";
import {URL} from "../../../util/path";

interface IReservationDetails{
    reservationId: number,
    checkInDate: string,
    checkOutDate: string,
    numGuests: number,
    status: string,
    setSuccess: (value:boolean)=>void
}

function OwnedReservationDetails({reservationId, checkInDate, checkOutDate, numGuests, status} : IReservationDetails){
    const [error, setError] = useState<String>("");
    const [actionResponse, setActionResponse] =useState<String>("");
    const [statusS, setStatusS] = useState<string>(status);
    

    const sendPatchRequest = async (data : string) => {
        try{
            let res = await axios.patch<iReservation>(`${URL}/reservations/${reservationId}`,
                {status:data},
                {withCredentials: true}
            )
            setStatusS(res.data.status);
            setActionResponse("Status was updated successfully!");
            } catch (error){
            if (axios.isAxiosError(error)) {
                const errorMessage = error.response?.data?.error || error.message;
                setError(errorMessage);
            } else {
                setError('Unknown error: ' + error);
            }    
        }
    }

    

    const acceptHandler = async () => {
        sendPatchRequest('ACCEPTED');
    }

    const rejectHandler = async ()=>{
        sendPatchRequest('REJECTED');
    }

    return (
        <Paper
            sx={{
                padding:'1rem',
                marginBottom: '1rem',
                boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)'
            }}
        >
            <Snackbar
                open={actionResponse ? true : false}
                autoHideDuration={10000} 
                onClose={() => setActionResponse("")}
                anchorOrigin={{ vertical: "bottom", horizontal: "left" }} 
            >
                <Alert onClose={() => {setActionResponse("")}} severity="success" sx={{ width: "100%" }}>
                {actionResponse}
                </Alert>
            </Snackbar>

            <Dialog open={error ? true: false} onClose={() => {setError("")}}>
                    <DialogTitle>Upps something went wrong!</DialogTitle>
                    <DialogContent>
                      <Typography>
                        {`${error} . Please try again`}
                        </Typography>
                    </DialogContent>
                </Dialog>
            <Grid container spacing={2}>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Check-In Date:</Typography>
                    <DatePicker 
                        value={dayjs(checkInDate)} 
                        disabled
                    />
                </Grid>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Check-Out Date:</Typography>
                    <DatePicker 
                        value={dayjs(checkOutDate)}
                        disabled
                    />
                </Grid>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Number of Guests:</Typography>
                    <TextField 
                        defaultValue={numGuests} 
                        type="number"
                        disabled
                    />
                </Grid>
                <Grid size={{xs:12, sm:6}}>
                    <Typography variant="h6">Status:</Typography>
                    <TextField defaultValue={statusS} disabled/>
                </Grid>
                {statusS === 'PENDING' ? (
                    <>
                        <Button 
                            variant="contained" 
                            color="primary" 
                            onClick={acceptHandler}>
                            Accept
                        </Button>
                        <Button variant="contained" color="error" onClick={rejectHandler}>Reject</Button>
                    </>
                ):(<></>)}
                
            </Grid>
            
        </Paper>
    );
}

export default OwnedReservationDetails;