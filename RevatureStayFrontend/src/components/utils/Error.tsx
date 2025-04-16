import { Alert, AlertTitle, Box, Typography } from "@mui/material";
import { ErrorOutline } from "@mui/icons-material";

function Error(props:{message:string}){
    return (
    <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', mt: 5 }}>
        <Alert 
        severity="error" 
        iconMapping={{
            error: <ErrorOutline />,
        }}
        sx={{ maxWidth: '500px', width: '100%' }}
        >
        <AlertTitle>Error</AlertTitle>
        {props.message || 'Something went wrong, please try again.'}
        </Alert>
        <Typography variant="caption" sx={{ mt: 2, textAlign: 'center' }}>
        If the problem persist, please contac us
        </Typography>
    </Box>
    );
}
export default Error;