import Image from "next/image";
import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected"
import {NavBarUser} from "@/ui/nav-bar/nav-bar-user";
import {NavBarOrga} from "@/ui/nav-bar/nav-bar-orga";
import {NavBarAdmin} from "@/ui/nav-bar/nav-bar-admin";
import {FormConnexion} from "@/ui/forms/form-connexion";
import FormInscription from "@/ui/forms/form-inscription";
import {Calendar} from "@/ui/calendar/calendar";
import {EventPresentation} from "@/ui/event/event-presentation";

export default function Home() {
    return (
        <div className=" justify-center bg-zinc-50 font-sans">
            <NavBarNotConnected/>

            <h1 className="font-medium text-black text-6xl text-center w-full py-10">Inscrivez-Vous</h1>
            <main className="flex min-h-screen w-full gap-8 px-16 sm:items-start">
                <FormInscription/>
            </main>
        </div>
    );
}
