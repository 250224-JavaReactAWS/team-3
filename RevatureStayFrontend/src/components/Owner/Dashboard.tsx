import { Box } from '@mui/material'
import { useEffect, useState } from 'react'
import { IHotel } from '../../interfaces/IHotel'
import axios from 'axios'

import HotelCardOwner from '../HotelManagment/HotelCardOwner'


function dashboard() {

    const [myHotels, setMyHotels] = useState<IHotel[]>([])

    //Let's log in with an owner user
    let login = async() =>{
        try{
            let res = await axios.post<{email: string, password: string}>("http://localhost:8080/users/login", 
                {email: "ksollam6@mlb.com", password: "Password1*"},
                {withCredentials: true}
            )
            console.log(res)

        }catch(error){
            console.error("Could not log in ", error)
        }
    }

    login()


    useEffect(() => {
        let getMyHotels = async() =>{
            try{
                let res = await axios.get<IHotel[]>("http://localhost:8080/hotels/my-hotels", 
                    {withCredentials: true}
                )
                setMyHotels(res.data)
            }catch(error){
                console.error("Could find your hotels ", error)
            }
        }


        getMyHotels()
    }, [])
    



  return (
    <Box sx={{width:"50%", margin: "auto"}}>
        <h1>My hotels</h1>

        <div style={{
        display: "grid",
        gridTemplateColumns: "auto auto auto",
        gap: "20px"
        }}>

        {
        myHotels.map((hotel) => {
            return (
            <HotelCardOwner hotelId={hotel.hotelId} name={hotel.name} address={hotel.address} cellphoneNumber={hotel.cellphoneNumber} description={hotel.description} ></HotelCardOwner>
            )
        })
        }
        
      </div>

    </Box>
  )
}

export default dashboard