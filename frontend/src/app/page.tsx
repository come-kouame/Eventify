import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected"
import {NavBarUser} from "@/ui/nav-bar/nav-bar-user";
import {NavBarOrga} from "@/ui/nav-bar/nav-bar-orga";
import {NavBarAdmin} from "@/ui/nav-bar/nav-bar-admin";
import {Calendar} from "@/ui/calendar/calendar";
import {EventPresentation} from "@/ui/event/event-presentation";

export default function Home() {
    return (
        <div className=" justify-center bg-zinc-50 font-sans">
            <NavBarNotConnected/>

            <h1 className="font-medium text-black text-6xl text-center w-full py-10">Nos prochains évènements</h1>
            <main className="flex min-h-screen w-full gap-8 px-16 sm:items-start">
                {/* Left calendar */}
                <div className="flex flex-col gap-4">
                    <Calendar date={new Date()} />
                    <Calendar date={new Date(new Date().getFullYear(), new Date().getMonth() + 1, 1)} />
                </div>

                {/* Event */}
                <div className="flex-1 flex justify-center">
                    <EventPresentation
                        title="Titre de l’événement"
                        description="Lorem ipsum dolor sit amet, consectetur adipiscing elit..."
                        date="Jeudi 19 Novembre 2014"
                        location="Lieu"
                        price={9}
                        image={null}
                        place_init={100}
                        place_current={42}
                    />
                </div>
            </main>
        </div>
    );
}
