export default function TweetCard({ tweet }) {
  return (
    <div className="bg-[#2C2E3A] p-4 rounded-xl shadow-lg shadow-black/50 mb-4">
      <div className="flex flex-col">
        <div className="flex items-center gap-2 mb-2">
          <span className="font-semibold text-[#B3B4BD]">
            {tweet.user.firstName} {tweet.user.lastName}
          </span>
          <span className="text-[#B3B4BD]/70">@{tweet.user.userName}</span>
        </div>
        <p className="text-[#B3B4BD] mb-2">{tweet.content}</p>
        <div className="flex gap-4 text-sm text-[#B3B4BD]/80">
          <span>❤️ {tweet.likeCount}</span>
          <span>🔁 {tweet.retweetCount}</span>
          <span>💬 {tweet.commentCount}</span>
        </div>
      </div>
    </div>
  );
}
