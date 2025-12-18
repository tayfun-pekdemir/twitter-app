import Login from "../components/Login";

export default function HomePage({setCurrentUser}) {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-[#141619] px-4">
      <h1 className="text-3xl md:text-4xl font-bold mb-8 text-center text-[#B3B4BD]">
        Welcome to Twitter clone
      </h1>

      <div className="w-full max-w-sm">
        <Login setCurrentUser={setCurrentUser} />
      </div>
    </div>
  );
}
