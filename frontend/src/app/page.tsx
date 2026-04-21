import {NavBar} from "@/ui/nav-bar/nav-bar"
import {Calendar} from "@/ui/calendar/calendar";
import {BlocEvenements} from "@/ui/event/bloc-evenements"
import {Hello} from "@/ui/message/hello";

export default function Home() {
    return (
        <div className=" justify-center bg-zinc-50 font-sans">
            <NavBar/>
            <h1 className="font-medium text-black text-6xl text-center w-full py-10">Nos prochains évènements</h1>
            <main className="flex min-h-screen w-full px-16 sm:items-start">
                {/* Left calendar */}
                <div className="flex flex-col gap-4 mx-4">
                    <Calendar date={new Date()} />
                    <Calendar date={new Date(new Date().getFullYear(), new Date().getMonth() + 1, 1)} />
                </div>

                {/* Event */}
                <BlocEvenements/>
            </main>
            <Hello/>


        </div>
    );
}
