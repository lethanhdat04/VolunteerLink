import { Greet } from "./components/greet";

export default function Home() {
  return (
    <main>
      <>
        <Greet />
        <h1 className="text-3xl font-bold underline">
          Hello world!
        </h1>
      </>
      
    </main>
  )
}