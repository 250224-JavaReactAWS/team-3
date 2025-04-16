import { Grid, Paper, Typography } from "@mui/material";
import iUser from "../../../interfaces/iUser";

function UserSection(user : iUser){
    return (
        // <Container>
            <Paper 
                sx={{
                padding: '16px',
                marginBottom: '16px',
                boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)'
                }}
            >
                <Grid container spacing={2}>
                <Grid size={{xs:12, md:6}}>
                    <Typography variant="h6">First Name:</Typography>
                    <Typography>{user.firstName}</Typography>
                </Grid>
                <Grid size={{xs:12, md:6}}>
                    <Typography variant="h6">Last Name:</Typography>
                    <Typography>{user.lastName}</Typography>
                </Grid>
                <Grid size={12}>
                    <Typography variant="h6">Email:</Typography>
                    <Typography>{user.email}</Typography>
                </Grid>
                </Grid>
            </Paper>
        // </Container>

    );
}

export default UserSection;