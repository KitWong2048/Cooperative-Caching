# Cooperative-Caching

## Description
The code simulate 3 different segment caching algrithms for P2P networks.

### 1) The centralized caching algorithm
which iterates through each segment following a descending
order of segment popularity. By starting with more
popular segments (i.e. more weighted segments in the optimization
statement), the algorithm is more likely to yield
a better optimization value. In each iteration, the k-median
heuristic is run to determine which k peers cache the segment,
where the k peers are selected from the set of peers with positive
residual capacity, and k is proportional to the segment
popularity and the number of segment suppliers so that more
popular segments are cached by a larger number of peers.

### 2) The sliding window caching strategy
in which each peer caches
segments within a sliding window of its play-point [1]. The
advantage of this strategy is that peers watching the same
portion of content can share. The supply implicitly matches
the popularity (or demand) of segments. However, as a peer
caches segments according to its play-point, the original
cached segments will no longer be available if the peer seeks
out some other positions of the media content.

### 3) The random caching strategy
in which each node randomly storges 
segments.

## How to
Open the project in "Simulator src" with eclipes
