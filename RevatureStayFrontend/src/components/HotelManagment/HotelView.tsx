import { Box, Button, ImageList, ImageListItem, ImageListItemBar, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { IHotel } from "../../interfaces/IHotel";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { ArrowBack, Book, Camera, EventAvailable, ImportContacts, LocationPin, Phone } from "@mui/icons-material";
import { InImage } from "../../interfaces/InImage";



function HotelView() {
    
    const {hotelId} = useParams<{hotelId: string}>();
    const [hotel, setHotel] = useState<IHotel>();
    const navigate = useNavigate()


    // const [images, setImages] = useState<InImage[]>([])

    // useEffect(() => {

    //   let getImageByHotelId = async () => {
    //     try {
    //       let res = await axios.get<InImage[]>(`http://localhost:8080/hotels/${hotelId}/images`)
    //       setImages(res.data)
    //     } catch (error){
    //       console.error("Error fetching hotel images ", error)
    //     }
        
    //   }

    //   getImageByHotelId()
      
    // },[])

    useEffect(() => {
        
            let getHotel = async () => {
                try {
                    let res = await axios.get<IHotel>(`http://localhost:8080/hotels/${hotelId}`)
                    setHotel(res.data)
                }
                catch (error){
                    console.error("Could not retrive hotel with id ", hotelId, " " , error)
                }
            }
        
        getHotel()
    },[])


  return (
    <>
        <div style={{ width: "50%", margin: "auto", position:"relative", marginTop:10}}>

            <Button color="secondary" variant="contained" style={{position:"absolute", top:10}} onClick={() => navigate("/hotels")} > <ArrowBack/> Back</Button>

            <div style={{justifyItems:"center", alignItems:"center"}}>
                <Typography variant="h1" fontSize={"34px"}>{hotel?.name}</Typography>
                <p><LocationPin sx={{verticalAlign:"middle"}}/> {hotel?.address}</p>
                <p><Phone sx={{verticalAlign:"middle"}}/>{hotel?.cellphoneNumber}</p>
                <p>{hotel?.description}</p>
            </div>
            
            

            <h2 style={{textAlign: "left"}}><Camera sx={{verticalAlign:"middle"}}/> Photos:</h2>
            <Box sx={{ width: "80%", height: "auto", display:"flex", gap:5 }}>
                <ImageList variant="masonry" cols={3} gap={8}>
                    {itemData.map((item) => (
                    <ImageListItem key={item.img}>
                        <img
                        srcSet={`${item.img}?w=248&fit=crop&auto=format&dpr=2 2x`}
                        src={`${item.img}?w=248&fit=crop&auto=format`}
                        alt={item.title}
                        loading="lazy"
                        />
                        <ImageListItemBar position="below" title={item.author} />
                    </ImageListItem>
                    ))}
                </ImageList>

                <div style={{width:"20%"}}>

                    <div style={{display:"flex", flexDirection:"column", gap:"10px", marginTop: 10}}>
                        <Button color="secondary" variant="contained">Check Availability</Button>
                        <Button color="secondary" variant="contained"><ImportContacts/>Book Now!</Button>
                    </div>

                    <h3>Room Types</h3>
                    <ul style={{
                      listStyle:"none"
                      }}>
                        <li>Single</li>
                        <li>Double</li>
                        <li>Triple</li>
                        <li>Suites</li>
                    </ul>

                    
                </div>
            </Box>

            
        </div>

        
    </>
  )

}

const itemData = [
    {
      img: 'https://images.unsplash.com/photo-1549388604-817d15aa0110',
      title: 'Bed',
      author: 'swabdesign',
    },
    {
      img: 'https://images.unsplash.com/photo-1525097487452-6278ff080c31',
      title: 'Books',
      author: 'Pavel Nekoranec',
    },
    {
      img: 'https://images.unsplash.com/photo-1523413651479-597eb2da0ad6',
      title: 'Sink',
      author: 'Charles Deluvio',
    },
    {
      img: 'https://images.unsplash.com/photo-1563298723-dcfebaa392e3',
      title: 'Kitchen',
      author: 'Christian Mackie',
    },
    {
      img: 'https://images.unsplash.com/photo-1588436706487-9d55d73a39e3',
      title: 'Blinds',
      author: 'Darren Richardson',
    },
    {
      img: 'https://images.unsplash.com/photo-1574180045827-681f8a1a9622',
      title: 'Chairs',
      author: 'Taylor Simpson',
    },
    {
      img: 'https://images.unsplash.com/photo-1530731141654-5993c3016c77',
      title: 'Laptop',
      author: 'Ben Kolde',
    },
    {
      img: 'https://images.unsplash.com/photo-1481277542470-605612bd2d61',
      title: 'Doors',
      author: 'Philipp Berndt',
    },
    {
      img: 'https://images.unsplash.com/photo-1517487881594-2787fef5ebf7',
      title: 'Coffee',
      author: 'Jen P.',
    },
    {
      img: 'https://images.unsplash.com/photo-1516455207990-7a41ce80f7ee',
      title: 'Storage',
      author: 'Douglas Sheppard',
    },
    {
      img: 'https://images.unsplash.com/photo-1597262975002-c5c3b14bbd62',
      title: 'Candle',
      author: 'Fi Bell',
    },
    {
      img: 'https://images.unsplash.com/photo-1519710164239-da123dc03ef4',
      title: 'Coffee table',
      author: 'Hutomo Abrianto',
    },
  ];

export default HotelView