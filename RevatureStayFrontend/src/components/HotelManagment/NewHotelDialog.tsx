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
import { URL } from '../../util/path';


export default function HotelFormDialog(props: {open:boolean, onClose: () => void, updateHotels: () => void }) {

    const [name, setName] = React.useState("")
    const [address, setAddress] = React.useState("")
    const [phone, setPhone] = React.useState("")
    const [description, setDescription] = React.useState("")

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
                let res = await axios.post<{name: string, address: string, cellphoneNumber: string, description: string}>(`${URL}/hotels`, 
                    {
                        name: name,
                        address: address,
                        cellphoneNumber: phone,
                        description: description
                    },
                    {withCredentials: true}
                )
                
                console.log(res);
                props.updateHotels()
              }catch(error){
                console.error("Could not add new hotel ", error)
              }
              
              props.onClose();
            },
          },
        }}
      >
        <DialogTitle>Add New Hotel</DialogTitle>
        <DialogContent sx={{display:"flex", flexDirection:"column", gap:"10px"}}>
          <DialogContentText>
            To add a new hotel, please fill the inputs
          </DialogContentText>
          <TextField id="name-input" type='text' label="Hotel Name" variant="outlined" onChange={(e: ChangeEvent<HTMLInputElement>) => {setName(e.target.value)}} required />
          <TextField id="address-input" type='address' label="Address" variant="outlined" onChange={(e: ChangeEvent<HTMLInputElement>) => {setAddress(e.target.value)}} required/>
          <TextField id="phone-input" type='phone' label="Phone Number" variant="outlined" onChange={(e: ChangeEvent<HTMLInputElement>) => {setPhone(e.target.value)}} required/>
          <TextField id="description-input" multiline rows={5} label="Description" onChange={(e: ChangeEvent<HTMLInputElement>) => {setDescription(e.target.value)}} required/>
        </DialogContent>
        <DialogActions>
          <Button onClick={props.onClose}>Cancel</Button>
          <Button type="submit">Add</Button>
        </DialogActions>
      </Dialog>
    </React.Fragment>
  );
}
