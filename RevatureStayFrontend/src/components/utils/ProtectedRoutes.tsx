import { Navigate, Outlet } from "react-router-dom";

function ProtectedRoutes(props:{isAutenticated:boolean}){
    return props.isAutenticated ? <Outlet/> : <Navigate to={"/login"}/>
}

export default ProtectedRoutes;