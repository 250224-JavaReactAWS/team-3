import { Container, Grid, Paper, Typography } from "@mui/material";
import { IHotel } from "../../../interfaces/IHotel";

function HotelSection(hotel:IHotel){
    return (
        // <Container>
            <Paper 
                sx={{
                padding: '1rem',
                marginBottom: '1rem',
                boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
                }}
            >
                <Grid container spacing={2}>
                    <Grid  size={12}>
                        <Typography variant="h5" gutterBottom>
                        {hotel.name}
                        </Typography>
                    </Grid>
                    <Grid size={12}>
                        <Typography variant="h6">Address:</Typography>
                        <Typography>{hotel.address}</Typography>
                    </Grid>
                    <Grid size={12}>
                        <Typography variant="h6">Phone Number:</Typography>
                        <Typography>{hotel.cellphoneNumber}</Typography>
                    </Grid>
                    <Grid size={12}>
                        <Typography variant="h6">Description:</Typography>
                        <Typography>{hotel.description}</Typography>
                    </Grid>
                </Grid>
            </Paper>
        // </Container>
    );   
}

export default HotelSection;