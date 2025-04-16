import { Box, Button, Container, Dialog, DialogContent, DialogTitle, Paper, Typography } from "@mui/material";
import { IHotel } from "../../../interfaces/IHotel";
import ReservationDetailsForm from "./ReservationDetailsForm";
import {  useState } from "react";
import { Dayjs } from "dayjs";
import HotelInfo from "./HotelInfo";
import AddRoomsSection from "./AddRoomsSection";
import iRoom from "../../../interfaces/iRoom";
import axios from "axios";
import iReservation from "../../../interfaces/iReservation";
import { useNavigate } from "react-router";
import {URL} from "../../../util/path";

interface ICreateReservation{
    hotel : IHotel
}

function CreateReservation({hotel} : ICreateReservation){
    const [checkin, setChecking] = useState<Dayjs|null>(null);
    const [checkout, setCheckout] =useState<Dayjs|null>(null);
    const [guests, setGuests ] = useState<number>(0);
    const [rooms, setRooms] = useState<iRoom[]>([]);
    const [isDisabled, setIsDisabled] = useState<boolean>(true);
    const [checkinError, setCheckinError] = useState<boolean>(false);
    const [checkoutError, setCheckoutError] = useState<boolean>(false);
    const [guestsError, setGuestsError] = useState<boolean>(false);
    const [error, setError] = useState<String>("");
    
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

    const sendPostRequest = async (data : iReservation) => {
        try{
            let res = await axios.post<iReservation>(`${URL}/reservations`,
              data,
              {withCredentials: true}
            )
            navigate(`/reservations/${res.data.reservationId}`)
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
                rooms: rooms,
                hotel: hotel,
                reservationId:0,
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
            await sendPostRequest(reservation);
        }
    }
    return(
        <>
            <Container>
                <Dialog open={error ? true: false} onClose={() => {setError("")}}>
                    <DialogTitle>Upps something went wrong!</DialogTitle>
                    <DialogContent>
                      <Typography>
                        {`${error} . Please try again`}
                        </Typography>
                    </DialogContent>
                </Dialog>
                
                <Paper 
                    sx={{
                        padding: '16px',
                        marginBottom: '16px',
                        boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
                    }}
                >
                    <ReservationDetailsForm 
                        checkin={checkin} 
                        checkout={checkout} 
                        guests={guests} 
                        setCheckin={setChecking}
                        setCheckout={setCheckout}
                        setGuests={setGuests}
                        checkinError={checkinError}
                        checkoutError={checkoutError}
                        guestsError={guestsError}
                    />
                    <Box sx={{
                        display: 'flex', 
                        gap: 8, 
                        p: 4, 
                        flexWrap: 'wrap',
                        justifyContent:'center'
                    }}>
                        <HotelInfo props={hotel}/>
                        <AddRoomsSection rooms={rooms} setRooms={(rooms:iRoom[]) => {
                            setRooms(rooms);
                            rooms.length > 0 ? setIsDisabled(false) : setIsDisabled(true);
                        }} />
                    </Box>
                    <Box>
                        <Button disabled={isDisabled} variant="contained" onClick={makeReservationHandler}>
                            Make reservation
                        </Button>
                    </Box>
                </Paper>
            </Container>
        </>
        
    );    
}

export default CreateReservation;