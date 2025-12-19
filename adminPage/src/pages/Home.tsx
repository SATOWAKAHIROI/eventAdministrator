import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div>
      <Link to="/events">イベント一覧</Link>
      <Link to="/notices">お知らせ</Link>
      <Link to="/overview">このページの概要</Link>
    </div>
  );
}
