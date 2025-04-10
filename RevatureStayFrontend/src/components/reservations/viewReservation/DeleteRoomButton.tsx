import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from "@mui/material";
import { useState } from "react";
interface IDeleteRoom{
    handleDelete:()=>void
}

function DeleteRoomButton({handleDelete} : IDeleteRoom){
    const [open, setOpen] = useState(false);

    return(
        <>
            <Button size="small" color="error" onClick={()=>{setOpen(true)}}>Delete</Button>
            <Dialog open={open} onClose={()=>{setOpen(false)}}>
                <DialogTitle>Eliminar Room</DialogTitle>
                <DialogContent>
                <DialogContentText>
                    Do you really wants to delete this room from your reservation?
                </DialogContentText>
                </DialogContent>
                <DialogActions>
                <Button onClick={()=>{setOpen(false)}} color="primary">
                    Back
                </Button>
                <Button 
                    onClick={()=>{
                        handleDelete()
                        setOpen(false);
                    }} 
                    color="error"
                >
                    Delete
                </Button>
                </DialogActions>
            </Dialog>
      </>
  );
};
export default DeleteRoomButton;


