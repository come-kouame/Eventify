import {NavBarAdmin} from "@/ui/nav-bar/nav-bar-admin";
import {NavBarOrga} from "@/ui/nav-bar/nav-bar-orga";
import {NavBarUser} from "@/ui/nav-bar/nav-bar-user";
import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected";
import {getUserStatus} from "@/services/client";

export const NavBar = async () => {
    const status= await getUserStatus();
    return (
        <>
            {status === "admin" && <NavBarAdmin/>}
            {status === "orga" && <NavBarOrga/>}
            {status === "user" && <NavBarUser/>}
            {status === null && <NavBarNotConnected/>}
        </>
    )
}