import { useContext, useEffect, useState } from "react";
import { IHotel } from "../../../interfaces/IHotel";
import axios from "axios";
import { Navigate, useNavigate, useParams } from "react-router-dom";
import { Box, CircularProgress } from "@mui/material";
import CreateReservation from "./CreateReservation";
import { authContext, AuthContextType } from "../../../App";
import Error from "../../utils/Error";
import {URL} from "../../../util/path";

function CreateReservationDisplayer(){
    const [error, setError] = useState<string>("");
    const [hotel, setHotel] = useState<IHotel|null>(null);
    const {hotelId} = useParams<{hotelId: string}>();
    const roleReference = useContext<AuthContextType|null>(authContext);
    const navigate = useNavigate();
    const defaultHotel : IHotel = {
        hotelId: 0,
        name: "",
        address: "",
        cellphoneNumber:  "",
        description: "",
    }
    useEffect(() => {   
        
        let getHotel = async () => {
            try{
                let res = await axios.get<IHotel>(`${URL}/hotels/${hotelId}`)
                setHotel(res.data)
            } catch (error){
                if (axios.isAxiosError(error)) {
                    const errorMessage = error.response?.data?.error || error.message;
                    setError(`${error.response?.status}: ${errorMessage}`);
                } else {
                    setError('Unknown error: ' + error);
                }    
            }
        }
        getHotel();
    },[])

    if(!hotel && !error){
        return(
            
            <Box
            display="flex"
            flexDirection="column"
            justifyContent="center"
            alignItems="center"
            height="10rem" 
            >
                <CircularProgress />
            
            </Box>
        );
    }

    if(error){
        return (
            <Error message = {error}/>
        );
    }

    if(roleReference?.role === "CUSTOMER"){
        return(
            <CreateReservation hotel={hotel ?? defaultHotel}/>
        );
    }
    if(roleReference?.role === "OWNER"){
        return(
            <Error message="Sorry, hotel owners are not allowed to make reservations"/>
        );
    }
    return(
        <Navigate to={"/login"}/>
    );
}

export default CreateReservationDisplayer;