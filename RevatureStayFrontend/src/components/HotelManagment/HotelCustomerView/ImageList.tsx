
import Box from '@mui/material/Box';
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import ImageListItemBar from '@mui/material/ImageListItemBar';
import axios from 'axios';
import { useState, useEffect } from 'react';
import { InImage } from '../../../interfaces/InImage';
import { URL } from '../../../util/path';

export default function ImageListCustomer(props: {hotelId: string | undefined}) {

  const [images, setImages] = useState<InImage[]>([])
  
      useEffect(() => {
  
        let getImageByHotelId = async () => {
          try {
            let res = await axios.get<InImage[]>(`${URL}/hotels/${props.hotelId}/images`)
            setImages(res.data)
          } catch (error){
            console.error("Error fetching hotel images ", error)
          }
        }
  
        getImageByHotelId()
        
      },[])


  return (
    <Box sx={{ width: "100%", height: "auto" }}>
      <ImageList variant="masonry" cols={2} gap={8}>
        {images.map((image) => (
          <ImageListItem key={image.image_id}>
            <img
              srcSet={`${image.url}?w=248&fit=crop&auto=format&dpr=2 2x`}
              src={`${image.url}?w=248&fit=crop&auto=format`}
              alt={image.alt}
              loading="lazy"
            />
            <ImageListItemBar position="below" title={image.alt} />
          </ImageListItem>
        ))}
      </ImageList>
    </Box>
  );
}

