import Image from "next/image";
import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected"
import {NavBarUser} from "@/ui/nav-bar/nav-bar-user";
import {NavBarOrga} from "@/ui/nav-bar/nav-bar-orga";
import {NavBarAdmin} from "@/ui/nav-bar/nav-bar-admin";

export default function Home() {
    return (
        <div className=" justify-center bg-zinc-50 font-sans dark:bg-black">
            <NavBarNotConnected/>
            <NavBarUser/>
            <NavBarOrga/>
            <NavBarAdmin/>
            <main
                className="flex min-h-screen w-full justify-between py-32 px-16 bg-white dark:bg-black sm:items-start">
                <p>Hello World</p>
            </main>
        </div>
    );
}
