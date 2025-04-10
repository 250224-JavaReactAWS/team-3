import { Grid, Typography } from "@mui/material";

interface AttributeProps{
    value:string,
    size?:number
}

function ReservationAttribute({value, size =2} : AttributeProps){
    return (
        <Grid size={size}>
            <Typography variant="body2">{value}</Typography>
        </Grid>
    );
}

export default ReservationAttribute;