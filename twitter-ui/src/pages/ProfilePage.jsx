import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import TweetCard from "../components/TweetCard";

export default function ProfilePage() {
  const { id } = useParams();
  const initialNewTweet = {
    userId: id,
    content: ""
  };

  const [tweets, setTweets] = useState([]);
  const [newTweet, setNewTweet] = useState(initialNewTweet);

  useEffect(() => {
    axios.get(`http://localhost:3000/tweet/findByUserId/${id}`, { withCredentials: true })
      .then(response => {
        setTweets(response.data);
        console.log("Response data:", response.data);})
      .catch(error => console.error(error.response.data.message || error.message));
  }, [id]);

  const newTweetHandleChange = (e) => {
    setNewTweet({
        ...newTweet,
        content: e.target.value
    });
  };

  const newTweetHandleSubmit = (e) => {
    e.preventDefault();
    axios.post("http://localhost:3000/tweet", newTweet, { withCredentials: true })
      .then(response => {
        setNewTweet(initialNewTweet);
        
        setTweets(prevTweets => [response.data, ...prevTweets]);
      })
      .catch(error => console.error(error.response?.data?.message || error.message));
  };

  return (
    <div className="min-h-screen bg-[#141619] text-[#B3B4BD] p-6">
      <h1 className="text-2xl font-bold mb-4">{id}'s profile</h1>

      <div className="m-auto flex flex-col w-full max-w-sm bg-[#2C2E3A] p-6 rounded-xl shadow-lg shadow-black/50">
        <form onSubmit={newTweetHandleSubmit}>
          <input
            type="text"
            name="tweet"
            value={newTweet.content}
            onChange={newTweetHandleChange}
            className="bg-[#141619] placeholder-[#B3B4BD] text-[#B3B4BD] rounded-md px-4 py-2 w-full"
          />
          <button type="submit" className="mt-2 bg-[#050A44] text-[#B3B4BD] py-2 rounded-md">
            Tweet
          </button>
        </form>
      </div>

      <div className="p-6">
        {tweets.map((tweet) => (
          <TweetCard key={tweet.tweetId} tweet={tweet} />
        ))}
      </div>
    </div>
  );
}
