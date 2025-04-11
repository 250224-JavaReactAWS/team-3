import { Add, ArrowBack } from "@mui/icons-material"
import { Button, Fab, Typography } from "@mui/material"
import ImageContainer from "./ImageContainer"
import { useNavigate, useParams } from "react-router-dom"
import ImgFormDialog from "./NewImageDialog"
import { createContext, useContext, useState } from "react"


function Images() {

  const {hotelId} = useParams<{hotelId: string}>()
  const [open, setOpen] = useState(false);
  const navigate = useNavigate()

    // Function to open the dialog
    const handleOpen = () => {
        setOpen(true);
    };

    // Function to close the dialog
    const handleClose = () => {
        setOpen(false);
    };


  return (
    <>
    <div style={{width:"50%", margin: "auto", justifyItems:"center", position:"relative"}}>
      <Button color="secondary" variant="contained" style={{position:"absolute", top:10, right: 10}} onClick={() => navigate("/my-hotels")} > <ArrowBack/> Back</Button>
      <Typography variant="h1" fontSize={34} sx={{margin: "10px 0"}}>Image Managment</Typography>
      <Fab color="primary" aria-label="add" onClick={() => handleOpen()}>
        <Add />
      </Fab>
      
      <ImageContainer hotelId={hotelId}/>

    </div>

    {/* Render the ImgFormDialog component conditionally */}
    {open && <ImgFormDialog hotelId={hotelId} open={open} onClose={handleClose} /> }
  
    </>
  )
}

export default Images