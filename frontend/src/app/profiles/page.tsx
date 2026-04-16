import {NavBarNotConnected} from "@/ui/nav-bar/nav-bar-not-connected";
import {NavBar} from "@/ui/nav-bar/nav-bar";


export default function Home() {
    return (
        <div className="min-h-screen bg-zinc-100 font-sans">
            <NavBar/>

            {/* Title */}
            <h1 className="text-5xl text-center py-10">
                Gestion des comptes
            </h1>

            <div className="flex px-10 gap-6">
                {/* Sidebar */}
                <div className="w-1/4 flex flex-col gap-6 rounded-lg">
                    <a href="/profiles/create" className="py-4 text-xl rounded-lg bg-indigo-300 text-center">
                        Créer un compte
                    </a>

                    <div className="bg-zinc-300 p-6 flex flex-col gap-4 rounded-lg">
                        <h2 className="text-3xl text-center">Filtres</h2>
                        <div className="text-lg flex flex-col gap-3">
                            <label className="flex items-center gap-2">
                                <input type="checkbox"/>
                                Administrateur
                            </label>
                            <label className="flex items-center gap-2">
                                <input type="checkbox"/>
                                Organisateur
                            </label>
                            <label className="flex items-center gap-2">
                                <input type="checkbox"/>
                                Participant
                            </label>
                            <label className="flex items-center gap-2">
                                <input type="checkbox"/>
                                Supprimé
                            </label>
                        </div>
                    </div>
                </div>

                {/* Table */}
                <div className="flex-1 bg-zinc-300 flex flex-col gap-4 p-6 rounded-lg">
                    <table className="w-full border-collapse">
                        <thead>
                            <tr className="bg-gray-400 text-left">
                                <th className="p-2">Nom</th>
                                <th className="p-2">Prénom</th>
                                <th className="p-2">E-mail</th>
                                <th className="p-2">Statut</th>
                                <th className="p-2">Modifier</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr className="border-t">
                                <td className="p-2">Morel</td>
                                <td className="p-2">Louis</td>
                                <td className="p-2">lm38550@gmail.com</td>
                                <td className="p-2">Administrateur</td>
                                <td className="p-2">
                                    <a href="/profiles/x" className="bg-blue-500 text-white px-4 py-1 rounded">
                                        Modifier
                                    </a>
                                </td>
                            </tr>

                            {/* Empty rows for layout */}
                            {Array.from({ length: 12 }).map((_, i) => (
                                <tr key={i} className="border-t">
                                    <td className="p-2">&nbsp;</td>
                                    <td className="p-2"></td>
                                    <td className="p-2"></td>
                                    <td className="p-2"></td>
                                    <td className="p-2"></td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}
