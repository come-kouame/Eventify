import { getUserByID } from "@/services/user";
import { Profile } from "@/ui/profile/profile";
import {NavBar} from "@/ui/nav-bar/nav-bar"

export default async function Page({ params }: { params: Promise<{ id: string }> }) {
    const { id } = await params;

    const user = await getUserByID(Number(id));

    return (
        <>
            <NavBar/>
            <div className="min-h-screen font-sans">
                <h1 className="font-medium text-black text-6xl text-center w-full py-10">
                    Profil de {user.nom} {user.prenom}
                </h1>

                <Profile user={user} />
            </div>
        </>
    );
}