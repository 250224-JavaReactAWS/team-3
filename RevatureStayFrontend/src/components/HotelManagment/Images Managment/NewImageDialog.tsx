import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import axios from 'axios';
import { ChangeEvent } from 'react';


export default function ImgFormDialog(props: {hotelId: any ,open:boolean, onClose: () => void }) {

    const [url, setUrl] = React.useState("")
    const [alt, setAlt] = React.useState("")

  return (
    <React.Fragment>
      <Dialog
        open={props.open}
        onClose={props.onClose}
        slotProps={{
          paper: {
            component: 'form',
            onSubmit:async (event: React.FormEvent<HTMLFormElement>) => {
              event.preventDefault();
              try{
                let res = await axios.post<{url: string, alt: string}>(`http://localhost:8080/hotels/${props.hotelId}/images`, 
                    {
                        url: url,
                        alt: alt
                    },
                    {withCredentials: true}
                )
                
                console.log(res);
                
              }catch(error){
                console.error("Could not submit your image ", error)
              }
              
              props.onClose();
            },
          },
        }}
      >
        <DialogTitle>Add An Image</DialogTitle>
        <DialogContent sx={{display:"flex", flexDirection:"column", gap:"10px"}}>
          <DialogContentText>
            To add a new image, please fill the inputs
          </DialogContentText>
          <TextField id="url-input" type='url' label="Image Url" variant="outlined" onChange={(e: ChangeEvent<HTMLInputElement>) => {setUrl(e.target.value)}} required />
          <TextField id="alt-input" type='text' label="Title" variant="outlined" onChange={(e: ChangeEvent<HTMLInputElement>) => {setAlt(e.target.value)}} required/>
        </DialogContent>
        <DialogActions>
          <Button onClick={props.onClose}>Cancel</Button>
          <Button type="submit">Add</Button>
        </DialogActions>
      </Dialog>
    </React.Fragment>
  );
}
