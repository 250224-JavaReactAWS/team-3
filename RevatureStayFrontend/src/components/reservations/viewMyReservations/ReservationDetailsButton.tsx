import { Button } from "@mui/material";

function ReservationDetailsButton({handler} : {handler : ()=> void}){
    return (
        <Button 
            variant="contained" 
            color="primary" 
            size="small" 
            onClick={handler}
            sx={{ marginTop: "0.5rem" }}
        >
            Details
        </Button>
    );
}

export default ReservationDetailsButton;