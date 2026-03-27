import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected"
import {NavBarUser} from "@/ui/nav-bar/nav-bar-user";
import {NavBarOrga} from "@/ui/nav-bar/nav-bar-orga";
import {NavBarAdmin} from "@/ui/nav-bar/nav-bar-admin";
import {Calendar} from "@/ui/calendar/calendar";
import {EventPresentation} from "@/ui/event/event-presentation";

export default function Home() {
    return (
        <div className="min-h-screen bg-zinc-100 font-sans">
            <NavBarNotConnected/>

            {/* Title */}
            <h1 className="text-5xl text-center py-10">Les Événements organisés</h1>

            <div className="flex px-10 gap-6">

                {/* Left panel */}
                <div className="w-1/4 flex flex-col gap-6">

                    {/* Create event button */}
                    <button className="bg-zinc-300 py-4 text-xl">
                        Créer un event
                    </button>

                    {/* Filters */}
                    <div className="bg-zinc-300 p-6 flex flex-col gap-4">
                        <h2 className="text-3xl text-center">Filtres</h2>

                        <div className="text-lg flex flex-col gap-3">
                            <label className="flex items-center gap-2">
                                <input type="checkbox" />
                                En cours
                            </label>
                            <label className="flex items-center gap-2">
                                <input type="checkbox" />
                                À valider
                            </label>
                            <label className="flex items-center gap-2">
                                <input type="checkbox" />
                                Annulé
                            </label>
                            <label className="flex items-center gap-2">
                                <input type="checkbox" />
                                Passé
                            </label>
                        </div>
                    </div>
                </div>

                {/* Right panel */}
                <div className="flex-1 bg-zinc-300 flex items-center justify-center">
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

            </div>
        </div>
    );
}
