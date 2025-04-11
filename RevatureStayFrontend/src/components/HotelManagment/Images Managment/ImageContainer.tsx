
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import ImageListItemBar from '@mui/material/ImageListItemBar';
import IconButton from '@mui/material/IconButton';
import { Delete } from '@mui/icons-material';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { InImage } from '../../../interfaces/InImage';
import { Typography } from '@mui/material';


export default function TitlebarImageList(props: {hotelId: any}) {

    const [images, setImages] = useState<InImage[]>([])
    const [shouldUpdate, setShouldUpdate] = useState(false)

    useEffect(() => {
        let getAllImages = async() =>{
            try{
                let res = await axios.get<InImage[]>(`http://localhost:8080/hotels/${props.hotelId}/images`, {withCredentials: true}) 
                setImages(res.data)
                console.log(res)
            }catch (error){
                console.error("Could not get all images ", error)
            }
        }

        getAllImages()

    },[shouldUpdate])
    

    return (
        <>
        {
            images.length !== 0 &&
            <ImageList sx={{ width: "90%", height: "auto", marginLeft: "auto", marginRight: "auto"}}>
            <ImageListItem key="Subheader" cols={3}>
            </ImageListItem>
            {images.map((item) => (
                <ImageListItem key={item.image_id}>
                <img
                    srcSet={item.url}
                    src={item.url}
                    alt={item.alt}
                    loading="lazy"
                />
                <ImageListItemBar
                    title={item.alt}
                    actionIcon={
                    <IconButton
                        sx={{ color: 'rgba(255, 255, 255, 0.54)' }}
                        aria-label={`info about ${item.alt}`}
                    >
                        <Delete />
                    </IconButton>
                    }
                />
                </ImageListItem>
            ))}
            </ImageList>
        }

        {
            images.length === 0 && 
            <Typography variant='h3' color='error' fontSize={26} marginTop={15}>Ups there are not any images here! Add one</Typography>
        }
        </>
    );

}

