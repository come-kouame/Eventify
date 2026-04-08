import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected";
import {Profile} from "@/ui/profile/profile";

export default function Home() {
    return (
        <div className="min-h-screen font-sans">
            {/* Header */}
            <NavBarNotConnected/>

            {/* Title */}
            <h1 className="font-medium text-black text-6xl text-center w-full py-10">
                Création d'un nouveau profil
            </h1>

            <Profile/>
        </div>
    );
}
