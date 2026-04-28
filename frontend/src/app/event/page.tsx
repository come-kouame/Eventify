import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected"
import {EventPresentation} from "@/ui/event/event-presentation";
import {NavBar} from "@/ui/nav-bar/nav-bar";
import {BlocMyEvenements} from "@/ui/event/bloc-my-evenements";

export default function Home() {
    return (
        <div className="min-h-screen bg-zinc-100 font-sans">
            <NavBar/>

            {/* Title */}
            <h1 className="text-5xl text-center py-10">
                Mes Évènements
            </h1>

            <div className="flex px-10 gap-6">

                {/* Right panel */}
                <div className="flex-1 bg-zinc-300 flex flex-col gap-4 p-6 rounded-lg">
                    <BlocMyEvenements/>
                </div>

            </div>
        </div>
    );
}
