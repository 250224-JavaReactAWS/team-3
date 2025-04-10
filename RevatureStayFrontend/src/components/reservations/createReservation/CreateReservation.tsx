import { Container, Paper } from "@mui/material";
import { IHotel } from "../../../interfaces/IHotel";
import HotelSection from "../viewReservation/HotelSection";
import ReservationDetailsForm from "./ReservationDetailsForm";
import { useState } from "react";
import { Dayjs } from "dayjs";

function CreateReservation(hotel:IHotel){
    const [checkin, setChecking] = useState<Dayjs|null>(null);
    const [checkout, setCheckout] =useState<Dayjs|null>(null);
    const [guests, setGuests ] = useState<number>(0);
    
    return(
        <>
            <Container>
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
                    />
                    <HotelSection {...hotel}/>
                </Paper>
            </Container>
        </>
        
    );    
}

export default CreateReservation;